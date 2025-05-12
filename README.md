Security setup:
To properly showcase the full functionality of this program, user must enter "midterm\src\main\resources" folder and create "secrets.properties" file (default value names are GOOGLE_CLIENT_SECRET and GITHUB_CLIENT_SECRET). after that they need to add needed secrets for oath2 login functions.
after that they need to enter applications.properties file in the same folder, and add needed client values.
With these actions the program should be fully functional.
The security setup requires user to have Authorization value in their header to access it. If the user doesn`t have a token auth value in their header they are only permited to visist api/auth/... part of the site.
In api/auth/...:
user can login using username and password (e.g. login info from bootstrap csv) using api/auth/login;
they can register using api/auth/reg, to do it you need "username", "password", and "email" values in the body(this process automatically returns auth token);
and for debug reasons I have decided to leave api/auth/tokens path, this path returns the list of all existing tokens.
After entering the correct UUID of the token in the header (using "Authorization" name for the value and inputing "Bearer " before the UUID value) all of the site should be accessible.
You can learn functionality of the rest of the site by reading my midterm submission.
In summary: there are 6 entities (not including ones used for authorization) User, Order, Seller, Product, Tag, Point.
Controllers have all CRUD services for all these entities.
mapping: .../api/"NAME_OF_THE_ENTITY" - for create, get all services
         .../api/"NAME_OF_THE_ENTITY"/"ID" - for update, get by id, delete services 
