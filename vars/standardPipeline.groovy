def call(body) {

        def config = [:]
        body.resolveStrategy = Closure.DELEGATE_FIRST
        body.delegate = config
        body()

        node {
            // Clean workspace before doing anything
            deleteDir()

            try {
                stage ('Clone') {
                    checkout scm
                }
                stage ('Build') {
                    bat ${projectName}
                }
                stage ('Tests') {
                    parallel 'static': {
                        echo 'shell scripts to run static tests...'
                    },
                    'unit': {
                         echo 'shell scripts to run unit tests...'
                    },
                    'integration': {
                        echo 'shell scripts to run integration tests...'
                    }
                }
                
            } catch (err) {
                currentBuild.result = 'FAILED'
                throw err
            }
        }
    }