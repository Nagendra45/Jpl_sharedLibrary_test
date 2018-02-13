#!Groovy

def call() {
			def to_list = "mcnagendra.prasad@misys.com balasundaram.A@misys.com"
											 
			//def cc_list = '' 
			String currentResult = currentBuild.result
			String previousResult = currentBuild.getPreviousBuild().result
			
			echo currentResult
			echo previousResult

			String subject = "$env.JOB_NAME $env.BUILD_NUMBER: $currentResult"

			String body = """
		<p>Build $env.BUILD_NUMBER ran on $env.NODE_NAME and terminated with $currentResult.
		</p>


		<p>See: <a href="$env.BUILD_URL">$env.BUILD_URL</a></p>

		"""

			String log = currentBuild.rawBuild.getLog(40).join('\n')
			if (currentBuild != 'SUCCESS') {
				body = body + """
		<h2>Last lines of output</h2>
		<pre>$log</pre>
		"""
			}

			if (to_list != null && !to_list.isEmpty()) {
				// Email on any failures, and on first success.
				if (currentResult != 'SUCCESS' || currentResult != previousResult) {
					script: emailext (subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
							  body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
							  <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
                              to: "mcnagendra.prasad@misys.com balasundara.A@misys.com")
				}
				echo 'Sent email notification'
			}
			if (currentResult = 'SUCCESS'){
				script: emailext (subject: "SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
							  body: """<p>SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
							  <p>Check console output at "<a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>"</p>""",
                              to: "mcnagendra.prasad@misys.com balasundara.A@misys.com")
			}
	}

