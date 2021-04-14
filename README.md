# ToDoList
Denne appen er en TodoList app, som skal kunne føre flere lister med relaterte oppgaver definert av bruker.

Appen inneholder to data klasser.
  Task holder en individuell oppgave, med en text gitt av bruker og en bool som viser om oppgaven er utført eller ikke.
  TodoList holder en liste med relaterte oppgaver definert av brukeren. Den har en text gitt av bruker og en liste med Tasks

Appen inneholder to skjermer
  En skjerm viser en oversikt over alle TodoList instansene og her er brukeren organiserer lister med oppgaver.
  En skjerm viser en oversikt over alle Tasks til en TodoList instanse og det er her brukeren lager nye tasks og avhukker gamle tasks som ferdige.

Det er to managere
  TaskManager holder business logikk for tasks som å legge til
  TodoListManager holder business logikk for TodoList

Det er en service
  TodoListService er ment for å kunne lagre data til fil og koble seg til Firebase backend for å laste opp data til skyen.
  Det brukes en broadcast reciver for å kunne sende data fra servicen tilbake.
  
  
  Link til APK: https://appdistribution.firebase.dev/i/e56fd02c11c765c5
