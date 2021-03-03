pipeline {
    agent none
    stages {
        stage('Build java') {
            agent {
                docker {
                    image 'maven:3-adoptopenjdk-14' 
                    args '-v /usr/local/jenkins-data/maven/.m2:/root/.m2' 
                }
            }
            steps {
                sh 'cd code/web && mvn -B -DskipTests clean package'
            }
        }
        // stage('Build vue') {
        //     agent {
        //         docker {
        //             image 'node:13'
        //             args '-v /usr/local/jenkins-data/node/node_modules:/var/jenkins_home/workspace/little_bird_master/node_modules'
        //         }
        //     }
        //     steps {
        //         sh 'pwd && cd vue && npm install yarn && npm --registry=https://registry.npm.taobao.org install && yarn build'
        //     }
        // }
        stage('Deliver') { 
            agent {
                // Equivalent to "docker build -f Dockerfile.build --build-arg version=1.0.2 ./build/
                // dockerfile {
                //     filename 'Dockerfile-admin'
                //     //dir 'build'
                //     //label '139.9.1.47:1180/wenanguo/cmcc-frontframe-admin'
                //     additionalBuildArgs  '--build-arg version=1.1.0'
                // }
                docker {
                    image '118.126.66.51/wenanguo/docker:v1.0.2'
                    registryUrl 'http://118.126.66.51/'
                    registryCredentialsId 'harbor'
                }
            }
            steps {
                script {
                    if(env.BRANCH_NAME == 'master1') {
                        echo "$BUILD_TAG"
                        echo '正式版本发布'
                    } else {
                        echo '测试版本发布'
                        //sh './jenkins/scripts/deliver-for-development.sh' 
                        //input message: 'Finished using the web site? (Click "Proceed" to continue)' 
                        sh "echo ok && cd code/web && docker build -t 118.126.66.51/wenanguo/ygsy_api:v1.0.$BUILD_NUMBER -f Dockerfile . && docker push 118.126.66.51/wenanguo/ygsy_api:v1.0.$BUILD_NUMBER"
                        sh "wget \"http://123.206.104.174:5000/update?images=118.126.66.51/wenanguo/ygsy_api:v1.0.$BUILD_NUMBER&project=ygsy_api\""
                        // sh "echo ok && cd vue && docker build -t 118.126.66.51/wenanguo/little_bird_vue:v1.0.$BUILD_NUMBER -f Dockerfile-vue . && docker push 118.126.66.51/wenanguo/little_bird_vue:v1.0.$BUILD_NUMBER"
                        // sh "wget \"http://123.206.104.174:5000/update?images=118.126.66.51/wenanguo/little_bird_vue:v1.0.$BUILD_NUMBER&project=little_bird_vue\""
                        //sh './jenkins/scripts/publish.sh' 
                    }
                }

                
            }
        }
    }
}