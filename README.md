# Overview

PasswordGrids is a password generator that helps to generate
[unique](http://xkcd.com/792/)
high security
passwords using grids.

```
A7  P2  GR  6F    W9  L3  HT  8S    U6  UA  5K  2Y    9X  TR  TW  JL
W6  4G  0G  40    HE  93  0G  J1    F6  F4  MP  ZD    27  0F  KZ  TL

L0  WK  F7  SE    0G  YL  52  50    DM  GD  DD  AP    S3  5F  9H  LF
6E  9M  P8  R6    J1  6M  24  AE    KW  4E  JM  JP    U1  71  SM  47

EF  8H  A2  R5    R5  Z1  WP  A6    A2  9L  E2  6H    80  H1  G4  5X
LZ  HT  TT  2M    HP  J1  U6  0R    SY  G7  KF  5H    R4  7J  PR  E6

7E  WD  3M  X5    SX  DM  H7  TY    9M  MZ  YY  XJ    0G  K6  SF  RR
L4  H5  6P  H6    YT  5G  MS  MY    Z3  Z2  MH  AE    M0  K8  XH  RR


9X  TR  TW  JL    1A  55  MX  5U    0X  5P  L0  WR    WJ  79  DK  YJ
27  0F  KZ  TL    Z4  TW  MZ  05    D4  78  46  3H    8E  Z3  M2  DM

S3  5F  9H  LF    HK  WT  28  LL    H0  FF  46  SR    JJ  09  0L  UY
U1  71  SM  47    16  9W  ZT  FG    H5  LU  UD  81    LE  JA  U5  JK

80  H1  G4  5X    TY  EP  ST  9E    XE  X7  K1  55    1F  5A  UF  1H
R4  7J  PR  E6    D6  7D  HJ  UT    6W  R3  XJ  TP    Z5  LA  8U  ES

0G  K6  SF  RR    M5  XG  7S  EW    AZ  TZ  Z1  X5    4Z  G7  MP  6X
M0  K8  XH  RR    AF  8H  EM  H0    91  P6  L0  P6    0G  76  EJ  2A


WJ  79  DK  YJ    GT  E8  4R  W9    61  0H  9R  Y2    UR  YH  7S  WE
8E  Z3  M2  DM    TX  JP  JK  WT    6M  R2  YK  ZU    K6  UR  TF  8E

JJ  09  0L  UY    KF  26  2G  UG    WS  9E  RF  Z8    ZR  5J  55  TS
LE  JA  U5  JK    8S  KM  DE  EM    F6  LU  26  HZ    RW  ES  LJ  GW

1F  5A  UF  1H    ZG  GK  08  U4    WM  2E  J9  DG    EY  5W  5H  E3
Z5  LA  8U  ES    H3  YT  FE  US    25  3R  81  FL    1F  J4  0M  Z9

4Z  G7  MP  6X    U4  E6  EL  86    5F  5Z  06  1Z    JL  KK  9Y  LR
0G  76  EJ  2A    XW  4F  AH  R6    FD  K2  W1  0Z    2K  UR  0X  95
```

This is the command line version. If you want a stand alone client-side
javascript version, please check:
[PasswordGrids JS](http://passwordgrids.com)

[Live version](http://passwordgrids.com)

# Getting started

## Requirements

- Requires Java JRE 7+ to run
- Requires Java JDK 7+, SBT 0.13 and Scala 2.11 to compile

## Compile

```
# sbt assembly
```

## Install

```
# cp target/scala-2.10/passwordgrids.jar ~/passwordgrids.jar
# alias grids='java -jar ~/passwordgrids.jar'
```

## Usage

```
# grids
```

### Example

The identifier pattern could be of the form:

```
[<user>@]domain
```

However you can use any strings that represent a unique account.

For websites/services where you will only have one account (or the default account),
like facebook, linkedin, ...

```
# grids
> master password: ****
> identifier: facebook
```

For a SSH server:

```
# grids
> master password: ****
> identifier: user@myserver.com

```

If a website, like your bank, force you to change your password regularly, use
an incremental number as user part of the identifier:

```
# pass
> master password: ****
> identifier: 1@bank
...


# grids
> master password: ****
> identifier: 2@bank
...
```

# Documentation

## Passwords

In order to be accepted in most places, the password should have the following
characteristics:

- length of 10+ characters
- a mix between lowercase, uppercase, number and [special char](https://www.owasp.org/index.php/Password_special_characters)

We propose to use a scheme like:

```
[<prefix>]<8+ characters from the grids>[<postfix>]

```
### Prefix/postfix

The prefix/postfix goal is to add some characters in order to match the ideal
password composition described above. This is not a primary security element.

The default grid gives only numbers and uppercase letters. Sometimes, the grid
will not give any numbers (7% with a 8 characters length pattern, not having any
letters is so unlikely that you can skip this case).

In this scenario, one should consider adding a special char, a lowercase letter
and a number.

A static 3 characters prefix/postfix like 'y0!', '@z3', ... is a good solution.

### Master password

The master password is a fundamental security element. The same password
will be used all the time and should be choosed wisely (not too short).

If the wrong password is input, the grids will just be different.

### Pattern

The pattern is a fundamental security element. It consists of picking a fixed
amount of ordered chars in the grids.

12 grids of 64 characters are generated (ideally all the grids
should fit on the screen without needing to scroll).

We recommend a length of 8+ characters (~ 2800 billions possibilities) in order
to make brute force attempts difficult.

Once defined, the pattern will be reused all the time.

To be secure, the pattern should not be obvious/linear, should pick elements in
different grids and different positions.

## Security

The security is achieved by using two fundamental security elements: the master password
and the pattern (secondary elements are the prefix/postfix and the identifier).

The grids are built with SHA-512 hashing:

```
SHA512(<master password>-<grid number>-<identifier>))
```

Each byte of the hash is then mapped to the corresponding alphabet character.

The security of the system is compromised when *both* fundamental security
elements are stolen. However, none of them are stored, and both are difficult to
steal (even more the pattern because it doesn't rely on any input devices).

Another advantage of the system is that you can generate your passwords in front
of people without risks.

### Limitations

![](http://imgs.xkcd.com/comics/security.png)

If an attacker gets one of your grid and its corresponding identifier, he could
have the ability to brute force the master password. That's why, even the identifier
input is hidden.

If an attacker gets multiple of your generated passwords (2+) and also your
master password, he could be able to determine the pattern and then generate
passwords for all your accounts.

# License

(The MIT License)

Copyright (c) 2015 Lorenzo Leonini

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the 'Software'), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

