# ReminderService
- Clone the repository to local
- Import as 'Existing Projects into Workspace' in Eclipse IDE
- Run Maven build (Right click : Run As -> Maven build)
- Deploy (Run As -> Run on Server)

REST API
- Add reminder 
POST 
Content-Type : application/json
http://localhost:8080/WM/Reminder/add  
Sample Json to post :
{"name":"File tax by 15", "description":"file pending taxes", "status":"NOT_DONE", "dueDate":"2017-10-15"}
{"name":"Submit project", "description":"Complete and upload the project", "status":"DONE", "dueDate":"2017-06-20"}

- List all reminders
GET
Content-Type : application/json
http://localhost:8080/WM/Reminder/list

- List all reminders with status and due date filter
GET
Content-Type : application/json
http://localhost:8080/WM/Reminder/list?status=DONE&startDate=2017-06-12&endDate=2017-06-22
for date filter, start date and end date both are needed

- Get reminder by Id
GET
Content-Type : application/json
http://localhost:8080/WM/Reminder/{id}

- Update reminder
PUT
Content-Type : application/json
http://localhost:8080/WM/Reminder/{id}
Sample Json : {"name":"File tax by 15", "description":"file pending taxes", "status":"NOT_DONE", "dueDate":"2017-10-15"}

- Delete reminder
Delete
Content-Type : application/json
http://localhost:8080/WM/Reminder/{id}



