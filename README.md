# multi-tenancy
Multi-tenancy refers to an architecture in which a single instance of a software application serves multiple tenants or customers.

Created a new SpringBoot application outside Pcaas API.

Used Docker compose to run two different Postgres data sources both with an identical employees table.
Implemented multi-tenancy with Spring Data JPA by routing data sources at runtime based on the current tenant identifier.
Set targetDatasources based on application.properties.
