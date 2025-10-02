# Создаем приватный ключ CA
openssl genrsa -out ca-key.pem 2048

# Создаем самоподписанный CA-сертификат
openssl req -new -x509 -days 3650 -key ca-key.pem -out ca-cert.pem -config openssl.cnf

# Создаем приватный ключ сервера
openssl genrsa -out server-key.pem 2048

# Создаем запрос на подпись сертификата (CSR)
openssl req -new -key server-key.pem -out server.csr -config openssl.cnf

# Подписываем CSR нашим CA
openssl x509 -req -in server.csr -CA ca-cert.pem -CAkey ca-key.pem -CAcreateserial -out server-cert.pem -days 365 -extfile openssl.cnf -extensions v3_req

# Объединяем сертификат сервера и CA в цепочку
cat server-cert.pem ca-cert.pem > full-chain.pem

# Создаем PKCS12 keystore
openssl pkcs12 -export -out keystore.p12 -inkey server-key.pem -in full-chain.pem -name "soa" -password pass:changeit
openssl pkcs12 -export -out cacerts.p12 -inkey server-key.pem -in full-chain.pem -name "soa" -password pass:changeit
