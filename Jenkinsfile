pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh './gradlew check'
                junit 'build/test-results/**/*.xml'
            }
        }
        stage('Build'){
            steps{
                sh './gradlew clean assemble'
            }
        }
        stage('Archive'){
            steps{
                archiveArtifacts 'build/libs/*.jar'
            }
        }
        stage('Deploy'){
            steps{
                pushToCloudFoundry cloudSpace: 'danderson-cnt', credentialsId: '89519d0c-3bea-43a8-bc7e-6035789d37ea', organization: 'solstice-org', target: 'api.run.pivotal.io'
            }
        }
    }
    post{
        success{
            cleanWs()
        }
    }
}