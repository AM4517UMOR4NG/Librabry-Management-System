@echo off
echo ========================================
echo    Library Management System Startup
echo ========================================
echo.

echo Starting Spring Boot Backend...
cd library-management-utspbold
start "Backend" cmd /k ".\mvnw.cmd spring-boot:run"
timeout /t 10 /nobreak >nul

echo.
echo Starting Vue.js Frontend (User Portal)...
cd frontend-vue
start "Vue Frontend" cmd /k "npm run dev"
timeout /t 5 /nobreak >nul

echo.
echo Starting React Frontend (Admin Dashboard)...
cd ../frontend-react
start "React Dashboard" cmd /k "npm run dev"
timeout /t 5 /nobreak >nul

echo.
echo ========================================
echo    System Starting...
echo ========================================
echo Backend: http://localhost:8481
echo Vue Frontend: http://localhost:5173
echo React Dashboard: http://localhost:5174
echo H2 Console: http://localhost:8481/h2-console
echo.
echo Default Users:
echo - Admin: admin / admin123
echo - User: user / user123
echo.
echo Press any key to exit this window...
pause >nul 