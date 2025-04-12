# dinner-club-backend
test project for Ates Soft

SETUP:
1. git clone
2. run MySQL server
3. run the project

*Important

-Want to note that the commits aren't done properly, there should be more commits with less code; every commits should be one logical unit

-Mail is implemented, so keep in mind that when you uncomment that fragment of code it will be really sent to gmails inserted into Guest table as test data(that isn't a problem, it's all people I know)

-If you want to test and see the mail implementation you can add someone manually in Guest with a gmail you can look up

*Implementation(some clarifications)

1.Secret location(automatic email reminder) - because the project is admin-orientied and there isn't register/login, the hidden location is solved by sending automatic reminder mail for the guests who confirmed their attendance with event details including reveal of the location

2.Event tracking- there are endpoints for findAll Event by different criteria with List as return type, but there are also Page return type services as an example to show different services would be called in controller if the database is too big and there are many records so it would need optimization

3.Analytical insights - implemented as quieres located in resources.analytical.queries
