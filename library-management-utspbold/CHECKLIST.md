# Library System Page Checklist

| Section | Page | Route | Roles | Core UI / Tasks | API (primary) | Priority | Status |
|--------|------|-------|-------|-----------------|---------------|----------|--------|
| Public | Home / Landing | `/` | GUEST | Hero, quick search, featured books, CTA | `GET /api/books?featured=true` | MVP | ☐ |
| Public | Search / Catalog | `/books` | GUEST, MEMBER | Search bar, filters, pagination, list/grid | `GET /api/books` | MVP | ☐ |
| Public | Book Detail | `/books/{id}` | ALL | Cover, metadata, availability, reserve btn | `GET /api/books/{id}`, `POST /api/reservations` | MVP | ☐ |
| Public | Author / Publisher | `/authors/{id}` | ALL | Bio, list of works | `GET /api/authors/{id}/books` | v1 | ☐ |
| Public | Help / FAQ | `/help` | ALL | FAQ, contact form | `POST /api/contacts` | v1 | ☐ |
| Public | Register | `/auth/register` | GUEST | Registration form, validation | `POST /api/auth/register` | MVP | ☐ |
| Public | Login | `/auth/login` | ALL | Login form, forgot password link | `POST /api/auth/login` | MVP | ☐ |
| Member | Dashboard | `/dashboard` | MEMBER | Current loans, due soon, fines | `GET /api/members/{id}/dashboard` | MVP | ☐ |
| Member | Profile | `/profile` | MEMBER | Edit profile, change password | `GET/PUT /api/members/{id}` | MVP | ☐ |
| Member | My Loans | `/my-loans` | MEMBER | List current/past loans, renew/return | `GET /api/loans?memberId`, `POST /api/loans/{id}/renew` | MVP | ☐ |
| Member | My Reservations | `/my-reservations` | MEMBER | Queue position, cancel reservation | `GET /api/reservations?memberId`, `DELETE /api/reservations/{id}` | MVP | ☐ |
| Member | Payments | `/payments` | MEMBER | Invoice list, pay fines | `GET /api/transactions?memberId`, `POST /api/transactions/pay` | v1 | ☐ |
| Member | Reviews | `/books/{id}/reviews` | MEMBER | Add/view reviews | `POST /api/books/{id}/reviews` | v1 | ☐ |
| Member | Notifications | `/notifications` | MEMBER | Notifications list | `GET /api/notifications?memberId` | v1 | ☐ |
| Member | History | `/history` | MEMBER | Full loan history, export CSV | `GET /api/members/{id}/history` | v1 | ☐ |
| Staff | Staff Dashboard | `/dashboard` | LIBRARIAN, ADMIN | Stats, quick actions | `GET /api/admin/dashboard` | MVP | ☐ |
| Staff | Loan Management | `/loans` | LIBRARIAN, ADMIN | Checkout, return, extend, mark lost | `POST /api/loans`, `POST /api/loans/{id}/return`, `PATCH /api/loans/{id}/extend` | MVP | ☐ |
| Staff | Reservations Queue | `/reservations/pending` | LIBRARIAN, ADMIN | Hold queues, notify next | `GET /api/reservations/pending`, `POST /api/reservations/{id}/fulfill` | v1 | ☐ |
| Staff | Member Management | `/members` | LIBRARIAN, ADMIN | Search, block/unblock, fines summary | `GET /api/members`, `PUT /api/members/{id}/block` | MVP | ☐ |
| Staff | Catalog Add/Edit | `/books/new` `/books/{id}/edit` | LIBRARIAN, ADMIN | Metadata form, copies entry, bulk import | `POST /api/books`, `PUT /api/books/{id}`, `POST /api/books/import` | MVP | ☐ |
| Staff | Book Copies | `/books/{id}/copies` | LIBRARIAN, ADMIN | List/manage copies | `GET /api/books/{id}/copies`, `POST /api/book-copies` | v1 | ☐ |
| Staff | Inventory | `/inventory` | LIBRARIAN, ADMIN | Inventory session, reconcile | `POST /api/inventory/sessions`, `GET /api/inventory/{id}` | v1 | ☐ |
| Staff | Scanner Interface | `/scan` | LIBRARIAN | Barcode/RFID scanning | `POST /api/scan` | v1 | ☐ |
| Staff | Transactions Log | `/transactions` | LIBRARIAN, ACCOUNTANT, ADMIN | List payments, refunds | `GET /api/transactions`, `POST /api/transactions/refund` | v1 | ☐ |
| Staff | Transfers | `/transfers` | LIBRARIAN, ADMIN | Inter-branch transfer | `POST /api/transfers` | Advanced | ☐ |
| Admin | Admin Dashboard | `/admin` | ADMIN, SUPER_ADMIN | System metrics, backups | `GET /api/admin/overview` | MVP | ☐ |
| Admin | User & Role Management | `/admin/users` | ADMIN | CRUD users, assign roles | `GET/POST/PUT/DELETE /api/users`, `GET/POST /api/roles` | MVP | ☐ |
| Admin | Settings | `/admin/settings` | ADMIN | Loan duration, fines, integrations | `GET/PUT /api/settings` | MVP | ☐ |
| Admin | Reports & Analytics | `/admin/reports` | ADMIN, AUDITOR | Generate/export reports | `GET /api/reports/...` | v1 | ☐ |
| Admin | Audit Log | `/admin/audit` | ADMIN, AUDITOR | Filter log, export | `GET /api/audit` | v1 | ☐ |
| Admin | Backup & Restore | `/admin/backup` | ADMIN | Trigger backup/restore | `POST /api/admin/backup` | v1 | ☐ |
| Admin | Import/Export | `/admin/import` `/admin/export` | ADMIN | CSV upload, preview | `POST /api/import/books`, `GET /api/export/books` | v1 | ☐ |
| Admin | Integrations | `/admin/integrations` | ADMIN | Manage API keys/webhooks | `GET/POST /api/integrations` | Advanced | ☐ |
| Admin | Maintenance | `/admin/maintenance` | ADMIN | Maintenance mode, logs | Custom endpoints | v1 | ☐ |
| Reports | Loans Report | `/reports/loans` | ADMIN | Chart + table | `GET /api/reports/loans` | v1 | ☐ |
| Reports | Top Books | `/reports/top-books` | ADMIN | Top borrowed/rated | `GET /api/reports/top-books` | v1 | ☐ |
| Reports | Overdue Report | `/reports/overdue` | LIBRARIAN, ADMIN | List overdue, notify | `GET /api/reports/overdue` | MVP | ☐ |
| Reports | Financial | `/reports/finance` | ACCOUNTANT, ADMIN | Fines & payments summary | `GET /api/reports/finance` | v1 | ☐ |
| Reports | Inventory | `/reports/inventory` | LIBRARIAN, ADMIN | Missing/damaged items | `GET /api/reports/inventory` | v1 | ☐ |
| Reports | Custom Analytics | `/reports/custom` | ADMIN, AUDITOR | Build query | Custom API | Advanced | ☐ |
| Dev | H2 Console | `/h2-console` | DEV | DB console | Built-in | Dev | ☐ |
| Dev | Swagger Docs | `/swagger-ui.html` | INTERNAL | API docs | `GET /v3/api-docs` | v1 | ☐ |
| Dev | Postman Runner | `/dev/postman` | DEV | Integration testing | n/a | Dev | ☐ |
| Dev | Email Template Editor | `/admin/mail-templates` | ADMIN | Edit email templates | `GET/PUT /api/templates/email` | v1 | ☐ |
| Dev | Feature Flags | `/admin/feature-flags` | ADMIN | Toggle features | `GET/PUT /api/feature-flags` | Advanced | ☐ |
| Dev | Accessibility/QA | `/dev/qa` | DEV | QA tools | n/a | Dev | ☐ |
| Component | Confirm Modal | global | ALL | Confirm actions | n/a | MVP | ☐ |
| Component | Scanner Modal | global | LIBRARIAN | Camera/scanner input | `POST /api/scan` | v1 | ☐ |
| Component | Bulk Import Preview | global | ADMIN | Preview import data | `POST /api/import/preview` | v1 | ☐ |
| Component | Toast/Notification Center | global | ALL | Real-time alerts | `GET /api/notifications` | MVP | ☐ |
