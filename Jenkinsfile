pipeline {

    agent any



    /*
        Tools
    */
    tools {
        maven 'microservicios'
    }


    /*
        Environment
    */
    environment {
        MODULE_NAME = "lecture_basic_module"
        HAS_INTEGRATION_TESTING = true
        NEEDS_STEP = "Deploy"
        // JENKINS_FAKE_CREDENTIALS = credentials('Fake')
    }


    /*
        Stages
    */
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                sh  '''
                        cd "${MODULE_NAME}"
                        mvn --version
                        mvn clean compile
                    '''
            }
        } /*
        stage('Unit Testing') {
            steps {
                echo 'Testing..'
                sh  '''
                        cd "${MODULE_NAME}"
                    '''
                // mvn test
            }
        } */
        stage('Build containers') {
            parallel {
                stage('Build and push') {
                    steps {
                        echo "Docker build tag and push ..."
                        // sh "docker-compose build"
                        // sh "docker tag jos3lu/shelteberus:latest"
                        // sh "docker push jos3lu/shelteberus:latest"
                    }
                }
                stage('Publishing test report') {
                    steps {
                        echo "Generating report..."
                    }
                }
            }
        }
        stage('Integration & Acceptance Testing') {
            when{ expression {env.HAS_INTEGRATION_TESTING.toBoolean() == true}}
            steps('Execute'){
                echo 'Executing integration tests...'
            }
        }
        stage('Deploy') {
            when {
                environment name: 'NEEDS_STEP', value: 'Deploy'
                /* allOf {
                    expression{env.REQUIRES_DEPLOYMENT == true}
                    expression{env.HAS_INTEGRATION_TESTING == true}
                } */
            }
            steps {
                input "Should we ship to prod?"
                echo 'Deploying main branch....'
                sh 'printenv'
            }
        }

    }


    /*
        Posts
        Also available after each stage
    */
    post {
        always {
            echo "Always post"
        }
        success {
            echo "Build succeeded post"
        }
        failure {
            echo "Job failure post"
            // mail to: "aaa.email@gmail.com",
            //      subject: "${env.MODULE_NAME} - Build has failed",
            //      body: "Check out the console output at ${env.MODULE_NAME}"
        }
        unstable {
            echo "Unstable completion status post"
        }
        changed {
            echo "Changed state of the pipeline post"
        }
        fixed {
            echo "Fixed state of the pipeline post" // "Previous run failed or unstable and now is successful"
        }
        cleanup {
            echo "Cleanup post"
        }
    }

}