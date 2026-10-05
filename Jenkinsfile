pipeline {
    agent any

    tools {
        maven 'Maven-3.9.16'
    }

    environment {
        DEPLOY_DIR  = 'C:\\CampusPlacementPortal\\deploy'
        JAR_NAME    = 'placement-portal-0.0.1-SNAPSHOT.jar'
        DEPLOY_PORT = '8082'
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

                // Stop any process on port 8082 using PowerShell
                bat 'powershell -NonInteractive -Command "$conn = Get-NetTCPConnection -LocalPort 8082 -ErrorAction SilentlyContinue; if ($conn) { $conn.OwningProcess | Sort-Object -Unique | ForEach-Object { Write-Host (\'Stopping PID: \' + $_); Stop-Process -Id $_ -Force -ErrorAction SilentlyContinue } }; Start-Sleep -Seconds 3; Write-Host \'Port 8082 cleared.\'"'

                // Copy JAR to deploy directory
                bat 'copy /Y "target\\%JAR_NAME%" "%DEPLOY_DIR%\\%JAR_NAME%"'

                // Launch Spring Boot app as detached background process
                bat 'powershell -NonInteractive -Command "Start-Process java -ArgumentList @(\'-Duser.timezone=Asia/Kolkata\',\'-jar\',\'%DEPLOY_DIR%\\%JAR_NAME%\',\'--server.port=%DEPLOY_PORT%\',\'--spring.datasource.url=jdbc:postgresql://127.0.0.1:5433/placement_db\',\'--spring.datasource.username=placement_user\',\'--spring.datasource.password=placement_password\') -NoNewWindow -RedirectStandardOutput \'%DEPLOY_DIR%\\app.log\' -RedirectStandardError \'%DEPLOY_DIR%\\app-error.log\'; Write-Host \'Application process launched.\'"'

                // Wait for Spring Boot to start up (30 seconds)
                bat 'powershell -NonInteractive -Command "Write-Host \'Waiting 30s for application startup...\'; Start-Sleep -Seconds 30; Write-Host \'Wait complete.\'"'
            }
        }

        stage('Verify') {
            steps {
                echo '=== STAGE: Verify ==='
                bat 'powershell -NonInteractive -Command "$maxAttempts=8; $attempt=0; $success=$false; while($attempt -lt $maxAttempts -and -not $success){$attempt++; try{$r=Invoke-WebRequest -Uri \'http://localhost:8082/\' -UseBasicParsing -TimeoutSec 10; Write-Host (\'HTTP Status: \'+$r.StatusCode); $success=$true}catch{Write-Host (\'Attempt \'+$attempt+\' failed, retrying...\'); Start-Sleep -Seconds 5}}; if(-not $success){Write-Host \'VERIFICATION FAILED\'; exit 1}; Write-Host \'Deployment verified successfully!\'"'
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
