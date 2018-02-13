#!Groovy

def call(){
		bat 'DATE /T'
		bat 'TIME /T'

		bat 'perl \\\\blrwinopfilsrv01\\OpicsCMRE_Work\\CMRE\\NP_Stuff\\scripts\\test.pl'

}
