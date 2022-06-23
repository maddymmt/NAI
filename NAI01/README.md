# k-NN
Celem jest napisanie programu, ktory pobiera następujące argumenty:  
**k**: dodatnia liczba naturalna będąca hiperparametrem k-NN.  
**train-set**: nazwa pliku zawierającego zbiór treningowy w postaci csv.  
**test-set**: nazwa pliku zawierajacego zbiór testowy w postaci csv.  

Program ma dokonać klasyfikacji k-NN wszystkich obserwacji z pliku test-set na podstawie pliku train-set oraz podać dokladność (accuracy) tej klasyfikacji (proporcję poprawnie zaklasyfikowanych przykładów testowych). 

Program ma też dostarczać testowy interfejs (niekoniecznie graficzny), który umożliwia (zapętlone) podawanie przez użytkownika pojedynczych wektorów do klasyfikacji i podaje ich etykietę k-NN na podstawie train-set.  

Opcjonalne rozszerzenie (dodatkowy punkt za aktywność): dowolną techniką (excel, python, etc.) zrobić wykres zależności dokładności (accuracy) od wartości k.
Przetestować na danych ze zbiorów treningowego i testowego znajdujących się w plikach iris.data i iris.test.data (ale program powinien umożliwiać wykorzystanie dowolnego zbioru).
