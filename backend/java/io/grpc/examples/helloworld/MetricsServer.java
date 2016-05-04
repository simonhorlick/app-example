package io.grpc.examples.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;

public class MetricsServer {

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    static final Logger logger = LoggerFactory.getLogger(MetricsServer.class);

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
            Channel ch = bootstrap.bind(8080).sync().channel();

            logger.info("Server listening on port {}", 8080);

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
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
            logger.info(fullHttpRequest.content().toString(StandardCharsets.UTF_8));
        }
    }
}
