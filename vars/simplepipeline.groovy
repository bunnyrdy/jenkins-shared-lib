def call(Map configMap){
    pipeline{
        agent{
            environment{
                employname = configMap.get('employname')
                company  = configMap.get('company')
                city  = configMap.get('city')
            }
        }
        options{
            disableConcurrentBuilds()
            timeout(time: 30, unit: 'MINUTES')
        }
        parameters{
            booleanParam(name: 'deploy',defaultValue: false, description: 'Toggle this value')
        }
        stages{
            stage('print employee details'){
                steps{
                    script{
                        echo "name is $employname"
                        echo "working in $company company"
                        echo "living in $city city"
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