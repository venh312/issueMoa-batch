pipeline {
    agent any
    
    environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    git branch: "master", credentialsId: "conf-git", url: "https://github.com/conf312/issueMoa-batch.git"
                }
            }
        }
        stage('Build') {
            steps {
                script {
                    sh "chmod 777 gradlew && ./gradlew bootJar"
                }
            }
        }
        stage('Dokcer Image Build') {
            steps {
                script {
                    sh "docker build -t issuemoa/batch ."
                }
            }
        }
        stage('Dokcer Run Conatiner') {
            steps {
                script {
                    def containerRunning = sh(script: 'docker inspect -f \'{{.State.Running}}\' issuemoa-batch 2>/dev/null', returnStatus: true) == 0

                    if (containerRunning) {
                        echo 'Container is already running'
                        sh "docker stop issuemoa-batch"
                        sh "docker rm issuemoa-batch"
                    }
                    
                    sh "docker run -d --name issuemoa-batch -p 20010:20010 -v /home/venh/logs:/var/log --network issuemoa issuemoa/batch"
                }
            }
        }
    }

    post {
        success {
            script {
                // 성공적인 빌드일 때 실행
                def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()

                slackSend (
                    channel: '#이슈모아', 
                    color: '#0100FF', 
                    message: "😄 이슈모아 Build & Release 성공! \n 💻 ${commitMessage} \n ${env.JOB_NAME},  buildNumber: [${env.BUILD_NUMBER}]"
                )
            }
        }
        
        failure {
            script {
                // 빌드 실패 시 실행
                def commitMessage = sh(script: "git log -1 --pretty=%B", returnStdout: true).trim()

                slackSend (
                    channel: '#이슈모아', 
                    color: '#FF0000', 
                    message: "😱 이슈모아 Build & Release 실패! \n 💻 ${commitMessage} \n ${env.JOB_NAME},  buildNumber: [${env.BUILD_NUMBER}]"
                )
            }
        }
    }
}
