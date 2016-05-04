This uses Kubernetes.

```
$ docker-machine create --driver virtualbox dev
$ eval "$(docker-machine env dev)"
$ export K8S_VERSION=$(curl -sS https://storage.googleapis.com/kubernetes-release/release/latest.txt)
$ docker run \
    --volume=/:/rootfs:ro \
    --volume=/sys:/sys:ro \
    --volume=/var/lib/docker/:/var/lib/docker:rw \
    --volume=/var/lib/kubelet/:/var/lib/kubelet:rw \
    --volume=/var/run:/var/run:rw \
    --net=host \
    --pid=host \
    --privileged=true \
    --name=kubelet \
    -d \
    gcr.io/google_containers/hyperkube-amd64:${K8S_VERSION} \
    /hyperkube kubelet \
        --containerized \
        --hostname-override="127.0.0.1" \
        --address="0.0.0.0" \
        --api-servers=http://localhost:8080 \
        --config=/etc/kubernetes/manifests \
        --cluster-dns=10.0.0.10 \
        --cluster-domain=cluster.local \
        --allow-privileged=true --v=2
$ curl -O "http://storage.googleapis.com/kubernetes-release/release/${K8S_VERSION}/bin/darwin/amd64/kubectl"
$ chmod 755 kubectl
$ PATH=$PATH:`pwd`
$ docker-machine ssh `docker-machine active` -N -L 8080:localhost:8080 &
$ kubectl config set-cluster test-doc --server=http://localhost:8080
$ kubectl config set-context test-doc --cluster=test-doc
$ kubectl config use-context test-doc
```

```
$ kubectl create -f backend/namespace-example-staging.yaml
$ kubectl create -f backend/namespace-example-prod.yaml
```

```
$ kubectl --namespace=staging create -f backend/backend.yaml
```

Push the built image to docker if it doesn't already exist:
```
$ docker load -i bazel-bin/backend/backend.tar
```

Ensure the three replicated backend pods started correctly:
```
$ kubectl --namespace staging get pods
```

Start SkyDNS, or discovery won't work..
```
$ export DNS_REPLICAS=1
$ export DNS_DOMAIN=cluster.local # specify in startup parameter `--cluster-domain` for containerized kubelet 
$ export DNS_SERVER_IP=10.0.0.10  # specify in startup parameter `--cluster-dns` for containerized kubelet 
sed -e "s/{{ pillar\['dns_replicas'\] }}/${DNS_REPLICAS}/g;s/{{ pillar\['dns_domain'\] }}/${DNS_DOMAIN}/g;s/{{ pillar\['dns_server'\] }}/${DNS_SERVER_IP}/g" skydns.yaml.in > ./skydns.yaml
# If the kube-system namespace isn't already created, create it
$ kubectl create namespace kube-system
$ kubectl create -f ./skydns.yaml
```

Start Prometheus:
```
$ kubectl create --validate=false -f backend/prometheus.yaml
```

Push the built image to docker if it doesn't already exist:
```
$ docker load -i bazel-bin/backend/prometheus.tar
```

FIXME(simon): I have no idea why this doesn't work out-of-the-box:
```
$ kubectl delete service prometheus
$ kubectl expose replicationcontroller prometheus --port=80 --target-port=9090 --external-ip=$(docker-machine ip dev)
$ kubectl delete service backend
$ kubectl expose replicationcontroller backend --external-ip=$(docker-machine ip dev)
$ kubectl annotate service backend prometheus.io/scrape='true'
$ kubectl annotate service backend prometheus.io/port='9090'
```

