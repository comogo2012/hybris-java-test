<html>
<head>
    <title>H2RD - Java Refactoring Test- Corey Morgan</title>
</head>
<body>
    <h1>Java Refactoring Test</h1>
    
    <p>Simple integration links for exercising the Rest user interfaces. 
       Click on the various links below, and hit the back button to continue
       your integration test. </p>
    
    <ul>
    
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%201&email=fn1@gmail.com&role=lifeguard&role=televangelist">
           Add Name 1</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%202&email=fn1@gmail.com&role=cheese%20straightener">
           Add Name 2, colliding emails with #1!</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%203&email=fn3@gmail.com&role=pooper%20scooper">
           Add Name 3</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%204&email=fn4@gmail.com&role=dog%20polisher&role=televangelist">
           Add Name 4</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/find/">
           Find Any</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/delete/?name=Fake%20Name%201">
           Delete name 1</a></p>
      </li>
  
      <li>  
        <p><a href="rest/users/delete/?name=Fake%20Name%203">
           Delete name 3</a></p>
      </li>
  
      <li>  
        <p><a href="rest/users/delete/?name=Foo">
           Delete wrong</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%201&email=update1@gmail.com&role=local%20updater">
           Update name 1</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/add/?name=Fake%20Name%203&email=update3@gmail.com&role=">
           Update name 3, no roles</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/search/?name=Fake%20Name%201">
           Search Name 1</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/search/?name=Fake%20Name%202">
           Search Name 2</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/search/?name=Foo">
           Search no one</a></p>
      </li>
      
      <li>  
        <p><a href="rest/users/foobar/?name=Foo">
           Bogus Rest Request</a></p>
      </li>
    
    </ul>
    
</body>
</html>
