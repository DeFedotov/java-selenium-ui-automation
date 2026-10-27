pipeline {
    agent any

    stages {
        stage('Run UI Tests') {
            steps {
                withEnv(['SELENIUM_REMOTE_URL=http://selenium-hub2:4444/wd/hub']) {
                    sh './gradlew clean test'
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}