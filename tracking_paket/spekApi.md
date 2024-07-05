# SPEK-API


## USER

### CREATE USER

Request :
- Method : `POST`
- Endpoint : `/authenticate`
- Header :
  - Content-Type : application/json
  - Accept : application/json
- Body :
```json
{
  "username": "username",
  "password": "password",

}
```

Response :

- Status : 201 Created



### LOGIN USER

Request :
- Method : `POST`
- Endpoint : `/authenticate/login`
- Header :
  - Content-Type : application/json
  - Accept : application/json
- Body :
```json
{
  "username": "username",
  "password": "password",

}
```

Response :

- Status : 200 ok
```json
{
    "status": {
        "code": 200,
        "description": "ok"
    },
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc0FkbWluIjp0cnVlLCJzdWIiOiJhZG1pbiIsImlhdCI6MTcxODI1MjYzOSwiZXhwIjoxNzE4MjcwNjM5fQ.TwpNHNNfiP_dnkZNi-AMeOxjJHvB-zpMurv-USDBUYg"
    }
}
```


## PAKET

### CREATE PAKET

Request :
- Method : `POST`
- Endpoint : `/api/paket/post`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "pengirim"        : "ahmad nur",
  "penerima"        : "ifandy sukma",
  "tujuanPengiriman": 1,
  "beratPaket"      : 2

}
```

Response :

- Status : 201 Created

## PENGIRIM

### CREATE PENGIRIM

Request :
- Method : `POST`
- Endpoint : `/api/pengirim/post`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "nama"        : "ahmad nur",
  "noTelepon"        : "0123455678"
}
```

Response :

- Status : 201 Created


## PENERIMA

### CREATE PENERIMA
ion : bearer-token
- Body :
```json
{
  "nama"        : "ahmad nur",
  "noTelepon"   : "0123455678"
}
```

Response :

- Status : 201 Created
Request :
- Method : `POST`
- Endpoint : `/api/penerima/post`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorizat

## SERVICE

### CREATE SERVICE

Request :
- Method : `POST`
- Endpoint : `/api/service/post`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "namaService"  : "super cepat",
  "hargaPerKg"   : 20000.00
}
```

Response :

- Status : 201 Created




## PENGIRIMAN

### CREATE PENGIRIMAN

Request :
- Method : `POST`
- Endpoint : `/api/pengiriman/post`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "service"  : "super cepat",
  "paket"   : "a3380426-bc17-4672-bd3a-bae5519f1222",
  "hargaPengiriman"     : 20000,
  "checkpointPengiriman": [
    {
        "lokasi"    :   "samarinda samarika",
        "isReceived":   true / false
        ...
    }
  ]
}
```

Response :

- Status : 201 Created



### GET PAKET YANG SUDAH SAMPAI

Request :
- Method : `GET`
- Endpoint : `/api/pengiriman/paketIsReceived`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "idPaket"  : "a3380426-bc17-4672-bd3a-bae5519f1222",
  "isReceived" : true
}
```

Response :

- Status : 200 Ok
- Body   :

```json
{
    "status": {
        "code": 200,
        "description": "Ok"
    },
    "data": {
        [
        "id" : "a3380426-bc17-4672-bd3a-bae5519f1222",
        "pengirim" : {
            "nama"     : "ahmad nur",
            "noTelepon": "0123455678"
        },
        "penerima" :{
            "nama"     : "ahmad nur",
            "noTelepon": "0123455678"
        },
        "lokasi" :{
            "idLokasi"   : "gdng-00001",
            "namaLokasi" : "cibaduyut",
            "alamat"     :  "jalan keramat banget"
        },
        "beratPaket"    : 2.0
        ]
    }
}

```



### GET PAKET YANG SUDAH MELEWATI CHECKPOINT TERTENTU

Request :
- Method : `GET`
- Endpoint : `/api/pengiriman/getWithCheckpoint`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :
```json
{
  "namaLokasi"  : "jakarta"
}
```

Response :

- Status : 200 Ok
- Body   :

```json
{
    "status": {
        "code": 200,
        "description": "Ok"
    },
    "data": {
        [
        "id" : "a3380426-bc17-4672-bd3a-bae5519f1222",
        "pengirim" : {
            "nama"     : "ahmad nur",
            "noTelepon": "0123455678"
        },
        "penerima" :{
            "nama"     : "ahmad nur",
            "noTelepon": "0123455678"
        },
        "lokasi" :{
            "idLokasi"   : "gdng-00001",
            "namaLokasi" : "cibaduyut",
            "alamat"     :  "jalan keramat banget"
        },
        "beratPaket"    : 2.0
        ]
    }
}

```



### GET ALL GUDANG / CHECKPOINT

Request :
- Method : `GET`
- Endpoint : `/api/gudang/getAll`
- Header :
  - Content-Type : application/json
  - Accept : application/json
  - Authorization : bearer-token
- Body :


Response :

- Status : 200 Ok
- Body   :

```json
{
    "status": {
        "code": 200,
        "description": "Ok"
    },
    "data": {
        [
            "id" : "a3380426-bc17-4672-bd3a-bae5519f1222",
            "namaLokasi" : "bali",
            "alamat" : "jalan kemerdekaan bagi seluruh rakyat"
        ]
    }
}

```

