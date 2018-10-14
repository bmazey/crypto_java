 <pre>
                      ___           ___                       ___                     ___
                     /  /\         /  /\          ___        /  /\        ___        /  /\
                    /  /:/        /  /::\        /__/|      /  /::\      /  /\      /  /::\
                   /  /:/        /  /:/\:\      |  |:|     /  /:/\:\    /  /:/     /  /:/\:\
                  /  /:/  ___   /  /:/~/:/      |  |:|    /  /:/~/:/   /  /:/     /  /:/  \:\
                 /__/:/  /  /\ /__/:/ /:/___  __|__|:|   /__/:/ /:/   /  /::\    /__/:/ \__\:\
                 \  \:\ /  /:/ \  \:\/:::::/ /__/::::\   \  \:\/:/   /__/:/\:\   \  \:\ /  /:/
                  \  \:\  /:/   \  \::/~~~~     ~\~~\:\   \  \::/    \__\/  \:\   \  \:\  /:/
                   \  \:\/:/     \  \:\           \  \:\   \  \:\         \  \:\   \  \:\/:/
                    \  \::/       \  \:\           \__\/    \  \:\         \__\/    \  \::/
                     \__\/         \__\/                     \__\/                   \__\/

</pre>

<h1>Project One</h1>

You can find our project running here: http://crypto-project.us-east-2.elasticbeanstalk.com/swagger-ui.html#/

<h2>Part One</h2>

For the first part of the assignment we'll be using the part one controller found here: http://crypto-project.us-east-2.elasticbeanstalk.com/swagger-ui.html#!/part45one45controller/postCiphertextUsingPOST

The controller accepts a ciphertext JSON and returns the decrypted plaintext.

Request

    POST http://crypto-project.us-east-2.elasticbeanstalk.com/api/partone/ciphertext
        {
          "ciphertext": [
            1,
            2,
            3
          ]
        }

Response

    HTTP 200
    {
      "plaintext": "mammate punners octette ... "
    }


<h2>Part Two</h2>

For the second part of the assignment we'll be using the part two controller found here: http://crypto-project.us-east-2.elasticbeanstalk.com/swagger-ui.html#!/part45two45controller/postCiphertextUsingPOST_1

The controller accepts a ciphertext JSON and returns the decrypted plaintext.

Request

    POST http://crypto-project.us-east-2.elasticbeanstalk.com/api/parttwo/ciphertext
        {
          "ciphertext": [
            81,
            82,
            83
          ]
        }

Response

    HTTP 200
    {
      "plaintext": "sampling jeopardous stovepipes harmony ... "
    }


Please direct any issues or questions to b.mazey@nyu.edu / sb6856@nyu.edu / fvl209@nyu.edu
