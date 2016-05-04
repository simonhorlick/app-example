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
