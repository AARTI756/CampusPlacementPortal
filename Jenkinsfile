pipeline {
    agent any

    tools {
        maven 'Maven-3.9.16'
    }

    environment {
        DEPLOY_DIR   = 'C:\\CampusPlacementPortal\\deploy'
        JAR_NAME     = 'placement-portal-0.0.1-SNAPSHOT.jar'
        DEPLOY_PORT  = '8082'
        DB_URL       = 'jdbc:postgresql://127.0.0.1:5433/placement_db'
    }

    stages {

        stage('Checkout') {
            steps {
                echo '=== STAGE: Checkout ==='
                checkout scmGit(
                    branches: [[name: '*/develop']],
                    userRemoteConfigs: [[url: 'https://github.com/AARTI756/CampusPlacementPortal.git']]
                )
                echo 'Source code checked out from develop branch.'
            }
        }

        stage('Build & Test') {
            steps {
                echo '=== STAGE: Build & Test ==='
                bat 'mvn clean test'
                echo 'All tests passed.'
            }
        }

        stage('Package') {
            steps {
                echo '=== STAGE: Package ==='
                bat 'mvn package -DskipTests'
                echo 'Spring Boot JAR packaged successfully.'
            }
        }

        stage('Deploy') {
            steps {
                echo '=== STAGE: Deploy ==='

                // Ensure deployment directory exists
                bat 'if not exist "%DEPLOY_DIR%" mkdir "%DEPLOY_DIR%"'

                // Stop any process currently listening on port 8082
                bat '''@echo off
for /f "tokens=5" %%a in ('netstat -ano 2^>nul ^| findstr ":8082 " ^| findstr "LISTENING"') do (
    echo Stopping existing process PID %%a on port 8082...
    taskkill /PID %%a /F 2>nul
)
echo Port 8082 cleared.
timeout /t 3 /nobreak >nul
'''

                // Copy the packaged JAR to the deploy directory
                bat 'copy /Y "target\\%JAR_NAME%" "%DEPLOY_DIR%\\%JAR_NAME%"'
                echo 'JAR copied to deploy directory.'

                // Start application as detached background process
                bat '''powershell -NonInteractive -Command ^
"Start-Process java ^
 -ArgumentList @('-Duser.timezone=Asia/Kolkata', '-jar', '%DEPLOY_DIR%\\%JAR_NAME%', '--server.port=%DEPLOY_PORT%', '--spring.datasource.url=%DB_URL%', '--spring.datasource.username=placement_user', '--spring.datasource.password=placement_password') ^
 -NoNewWindow ^
 -RedirectStandardOutput '%DEPLOY_DIR%\\app.log' ^
 -RedirectStandardError '%DEPLOY_DIR%\\app-error.log'"
'''
                echo 'Application process launched. Waiting for startup...'

                // Give the Spring Boot app time to start
                bat 'timeout /t 30 /nobreak >nul'
            }
        }

        stage('Verify') {
            steps {
                echo '=== STAGE: Verify ==='
                bat '''powershell -NonInteractive -Command ^
"$maxAttempts = 8; $attempt = 0; $success = $false; ^
while ($attempt -lt $maxAttempts -and -not $success) { ^
    $attempt++; ^
    try { ^
        $r = Invoke-WebRequest -Uri 'http://localhost:8082/' -UseBasicParsing -TimeoutSec 10; ^
        Write-Host ('Deployment verified! HTTP Status: ' + $r.StatusCode); ^
        $success = $true ^
    } catch { ^
        Write-Host ('Attempt ' + $attempt + ' failed - retrying in 5s...'); ^
        Start-Sleep -Seconds 5 ^
    } ^
}; ^
if (-not $success) { Write-Host 'VERIFICATION FAILED'; exit 1 }"
'''
            }
        }
    }

    post {
        success {
            echo '=========================================='
            echo 'PIPELINE SUCCESS'
            echo 'Application deployed at http://localhost:8082/'
            echo '=========================================='
        }
        failure {
            echo 'PIPELINE FAILED - Review stage logs above for details.'
        }
    }
}
