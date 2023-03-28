
#include <Servo.h>
#include <SoftwareSerial.h>
 
Servo meuservo; // Inicializa o servo
int pinServo = 9;  // Define que o Servo está conectado a Porta 9

const byte rxPin = 12; // connctar o tx do modulo bluetooth
const byte txPin = 13; // conectar o rx do modulo bluetooth

double servoPosition;

SoftwareSerial mySerial (rxPin, txPin);
 
void setup() { 
  meuservo.attach(pinServo); 
  mySerial.begin(9600);
  Serial.begin(9600);
  meuservo.write(180);
} 
 
void loop() {
  String positionText;
  int ler;
  while (mySerial.available()>0){
    do {
      ler = mySerial.read();
      if(ler!=124) {
        char character = ler;
        positionText.concat(character);
      }
      movServo();
   } while(ler!=124);
   servoPosition = positionText.toDouble();
   positionText = "";
   movServo();
   delay(50);
  }
}

void movServo() {
  int angulo;
  angulo = mapD(servoPosition, -9.9, 9.9, 0, 180); // Associa o valor da posição do eixo x ao valor do ângulo do servo
  meuservo.write(angulo);
}

double mapD(double x, double in_min, double in_max, double out_min, double out_max) {
  return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
}