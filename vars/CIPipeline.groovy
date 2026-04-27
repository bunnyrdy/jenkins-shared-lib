def call(Map config) {
    pipeline{
        agent {label 'AGENT-1'}
        options{
            disableConcurrentBuilds()
            timeout(time: 30, unit: 'MINUTES')
        }

        
        environment {
            PROJECT = config.get('project')
            COMPONENT = config.get('component')
            PROGRAMMING = config.get('codetype')

        }
        parameters{
            booleanParam(name: 'deploy',  defaultValue: false, description: 'Toggle this value')
        }
        stages {
            stage('Read version'){
                steps{
                    script{
                        echo "reading the project:$PROJECT component:$COMPONENT"
                    }
                }
            }
            stage('Unit test'){
                steps {
                    script{ 
                        sh """
                            echo "Unit tests are created by developers, we will configure the command like npm test"
                        """
                    }
                }
            }
             /* stage('Run Sonarqube') {
                environment {
                    scannerHome = tool 'sonar-scanner-7.1';
                }
                steps {
                withSonarQubeEnv('sonar-scanner-7.1') {
                    sh "${scannerHome}/bin/sonar-scanner"
                    // This is generic command works for any language
                }
                }
            }
            stage("Quality Gate") {
                steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
                }
            } */
            stage('install dependencies'){
                 
                steps{
                    // when{

                    //     expression {  params.PROGRAMMING == 'java' }

                    // } 
                    
                    // script{
                    //     echo "install the. required dependencies"
                    // }
                    switch (env.PROGRAMMING) {
                    case 'java':
                        echo "Installing Maven, JDK..."
                        // sh 'apt install -y maven openjdk-17-jdk'
                        break
                    case 'python':
                        echo "Installing pip, venv..."
                        // sh 'pip install -r requirements.txt'
                        break
                    case 'go':
                        echo "Installing Go modules..."
                        // sh 'go mod download'
                        break
                    default:
                        error("Unsupported language: ${env.PROGRAMMING}")
                }
                }
            }
            stage('sonarcube testing'){
                steps{
                    script{
                        echo "run the sonnar cube testing "
                    }
                }
            }
            stage('building image'){
                steps{
                    script{
                        echo "this step involves building image "
                    }
                }
            }
             stage('pushing image to ecr'){
                steps{
                    script{
                        echo "this step involves pushing  image to ecr "
                    }
                }
            }

            
        }
        post{
            always {
                echo 'i will run always'
            }
            success {
                echo 'i will run when pipeline is success'
            }
            failure{
                echo 'i will run this fails'
            }
        }
    }
    
}