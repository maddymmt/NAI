# Sieć jednowarstwowa

Celem projektu jest stworzenie sieci jednowarstwowej identyfikującej język, w jakim napisany jest tekst wejściowy. W pliku lang.zip znajduje się zbiór tekstów w trzech językach – angielskim, polskim i niemieckim. Aby zaklasyfikować dany tekst należy zliczyć częstotliwość występowania każdej z liter alfabetu łacińskiego. Na potrzeby tego zadania ignorujemy wszystkie niestandardowe litery (polskie znaki, etc.). Zliczamy tylko częstotliwości 26 podstawowych liter alfabetu, pomijając wszystkie inne znaki.  
Dla tekstu wejściowego generujemy 26-elementowy wektor wejściówy zawierający liczbę wystąpień każdej z liter. Następnie należy znormalizować wektor.  
Wyjście sieci powinno mieć reprezentację lokalną: do każdego neuronu przypisujemy jeden z języków. Dla danego tekstu wartość wyjściową 1 powinien mieć neuron reprezentujący język tekstu, a pozostałe wartość 0. Aby klasyfikować język tekstu wejściowego, wybieramy neuron z maksymalną aktywacją. Możemy wykorzystać neurony ze skokową funkcją aktywacji (w tym wypadku możemy użyć implementacji z ostatniego projektu), lub sigmoidalną.  
W pliku lang.test.zip znajduje się zbiór testowy, którym testujemy wytrenowaną sieć.  
Program powinien zapewniać interfejs umożliwiający wklejenie tekstu i rozpoznanie jego języka.
