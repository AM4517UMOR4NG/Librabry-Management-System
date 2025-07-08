Write-Host "========================================" -ForegroundColor Cyan
Write-Host "   Library Management System Startup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Write-Host "Starting Spring Boot Backend..." -ForegroundColor Yellow
Set-Location "library-management-utspbold"
Start-Process powershell -ArgumentList "-NoExit", "-Command", ".\mvnw.cmd spring-boot:run" -WindowStyle Normal
Start-Sleep -Seconds 10

Write-Host ""
Write-Host "Starting Vue.js Frontend (User Portal)..." -ForegroundColor Yellow
Set-Location "frontend-vue"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "npm run dev" -WindowStyle Normal
Start-Sleep -Seconds 5

Write-Host ""
Write-Host "Starting React Frontend (Admin Dashboard)..." -ForegroundColor Yellow
Set-Location "frontend-react"
Start-Process powershell -ArgumentList "-NoExit", "-Command", "npm run dev" -WindowStyle Normal
Start-Sleep -Seconds 5

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "   System Starting..." -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host "Backend: http://localhost:8481" -ForegroundColor White
Write-Host "Vue Frontend: http://localhost:5173" -ForegroundColor White
Write-Host "React Dashboard: http://localhost:5174" -ForegroundColor White
Write-Host "H2 Console: http://localhost:8481/h2-console" -ForegroundColor White
Write-Host ""
Write-Host "Default Users:" -ForegroundColor White
Write-Host "- Admin: admin / admin123" -ForegroundColor White
Write-Host "- User: user / user123" -ForegroundColor White
Write-Host ""
Write-Host "Press any key to exit..." -ForegroundColor Gray
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown") 