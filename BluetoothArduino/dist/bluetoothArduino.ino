#include <SoftwareSerial.h>

const byte rxPin = 12; // conectar o tx do modulo arduino
const byte txPin = 13; // conectar o rx do modulo arduino

SoftwareSerial mySerial (rxPin, txPin);

void setup() {
  // put your setup code here, to run once:
  mySerial.begin(9600);
  Serial.begin(9600);
}

void loop() {
  int ler;
  String frase;
  while (Serial.available()>0){
   delay(10);
   ler = Serial.read();
   mySerial.write(ler); 
   delay(10);
  }
  
  while (mySerial.available()>0){
   delay(10);
   ler = mySerial.read();
   char character = ler;
   frase.concat(character);
   delay(10);
   
  }
  if (frase.length()) {
    Serial.println(frase);    
  }
  delay(10);
}
