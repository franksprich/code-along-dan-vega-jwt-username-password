Create a private key on the terminal with:
```shell
openssl genrsa -out origin 2048
```

From the private key we can extract the public key with:
```shell
openssl rsa -in origin -pubout -out async_key.pub
```

Create a PM encoded PKCS8 Format:
Think of non-printable characters to be removed:
```shell
openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in origin -out async_key.prv
```
