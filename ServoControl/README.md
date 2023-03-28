# Servo Control
## Informações
O aplicativo conecta-se à um dispositivo bluetooth e envia dados do posicionamento do smartphone em relação ao seu eixo X.<br/>
Essa informação é coletada por meio do Acelerômetro do smartphone.

A informação da posição é enviada comno uma string composta pela posição em ponto flutuante adicionada de um | (pipe).<br/>
O pipe é utilizado para informar o final do dado de posição enviado para o dispositivo receptor.

Dentro da pasta 'dist/' desse diretório está localizado o arquivo .apk já buildado. <br/>
Há também o programa .ino de exemplo, utilizado por um Arduino Uno R3 para fazer o controle de um servo motor.
