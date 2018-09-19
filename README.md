#jdlGenerator
##CommandLine examples

####JDL-Generation:
-jdlGeneration
-ignoreFields additionalDocuments,group,vehicles,client,contragent,departmentes,trailedVehicles,cause,documents,automobileTransport,groups
-sourceFolder /home/igorch/work/jdl-test/csv
-targetFile /home/igorch/work/jdl-test/csv/docs.jh
-mapstruct
-generateServiceFor *
-microservice partnerDocuments
-gateway partnerGateway
-targetResourceFolder /home/igorch/work/jdl-test/csv/i18n

####GID-Generation:
-gidGeneration
-entities /home/igorch/work/gid-test/entities.jdl
-relations /home/igorch/work/gid-test/relations.jdl
-actions /home/igorch/work/gid-test/actions.jdl
-targetResourceFolder /home/igorch/work/gid-test/target