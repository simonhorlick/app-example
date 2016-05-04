package io.grpc.examples.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.logging.LoggingHandler;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import static io.netty.util.CharsetUtil.UTF_8;

public class MetricsServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    private final CollectorRegistry registry;

    private final int port;

    static final Logger logger = LoggerFactory.getLogger(MetricsServer.class);

    public MetricsServer(CollectorRegistry registry, int port) {
        this.registry = registry;
        this.port = port;
    }

    public void start() {
        // Configure the server.
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // Pipeline for each new incoming request.
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new HttpServerCodec());
                            p.addLast(new HttpObjectAggregator(65536));
                            p.addLast(new MetricsRequestHandler());
                        }
                    });

            // Bind and listen on the given port.
            Channel ch = bootstrap.bind(port).sync().channel();

            logger.info("Server listening on port {}", port);

            // Block indefinitely.
            //ch.closeFuture().sync();

        } catch(Throwable th) {
            logger.error("Error", th);
        }
    }

    public void shutdown() {
        logger.info("Server shutdown started");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        logger.info("Server shutdown completed");
    }

    private class MetricsRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

            // Reject bad requests.
            if (!req.decoderResult().isSuccess()) {
                sendError(ctx, HttpResponseStatus.BAD_REQUEST);
                return;
            }

            // Allow only GET methods.
            if (req.method() != HttpMethod.GET) {
                sendError(ctx, HttpResponseStatus.FORBIDDEN);
                return;
            }

            if(!"/metrics".equals(req.uri())) {
                sendError(ctx, HttpResponseStatus.NOT_FOUND);
                return;
            }

            StringWriter writer = new StringWriter();
            TextFormat.write004(writer, registry.metricFamilySamples());

            ByteBuf body = Unpooled.copiedBuffer(writer.toString(), UTF_8);

            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);

            response.headers().add(HttpHeaderNames.CONTENT_TYPE, TextFormat.CONTENT_TYPE_004);
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());

            sendHttpResponse(ctx, req, response);
        }

        private void sendHttpResponse(ChannelHandlerContext ctx,
                FullHttpRequest req, FullHttpResponse res) {
            // Generate an error page if response getStatus code is not OK (200).
            if (!res.status().equals(OK)) {
                ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), UTF_8);
                res.content().writeBytes(buf);
                buf.release();
                res.headers().set(CONTENT_LENGTH, res.content().readableBytes());
            }

            // Send the response and close the connection if necessary.
            ChannelFuture f = ctx.channel().writeAndFlush(res);
            if (!req.headers().contains(KEEP_ALIVE) || !res.status().equals(OK)) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }

        private void sendError(ChannelHandlerContext ctx,
                HttpResponseStatus status) {
            ByteBuf content = Unpooled.copiedBuffer("Failure: " + status + "\r\n",
                    UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1,
                    status, content);

            response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

            // Close the connection as soon as the error message is sent.
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}
