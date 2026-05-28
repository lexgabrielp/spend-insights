# API Examples

Register:

```bash
curl -X POST http://localhost:8080/api/auth/register -H 'Content-Type: application/json' -d '{"name":"Demo","email":"demo@example.com","password":"password123"}'
```

Upload CSV:

```bash
TOKEN=...
curl -X POST http://localhost:8080/api/uploads/csv -H "Authorization: Bearer $TOKEN" -F file=@samples/transactions.csv
```

Ask AI:

```bash
curl -X POST http://localhost:8080/api/chat -H "Authorization: Bearer $TOKEN" -H 'Content-Type: application/json' -d '{"message":"How much did I spend on software?"}'
```
