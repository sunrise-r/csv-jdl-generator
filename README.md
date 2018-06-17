#gdlGenerator
#Commandline example
-ignoreFields additionalDocuments,group,vehicles,client,contragent,departmentes,trailedVehicles,cause,documents,automobileTransport,groups
-sourceFolder /home/igorch/work/jdl-test/csv
-targetFile /home/igorch/work/jdl-test/csv/docs.jh
-mapstruct
-generateServiceFor *
-microservice partnerDocuments
-gateway partnerGateway
-targetResourceFolder /home/igorch/work/jdl-test/csv/i18n
