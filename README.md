# Overview

PasswordGrid is a password generator that helps to generate
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
# cp target/scala-2.10/passwordgrid-cli-assembly-*.jar ~/passwordgrid.jar
# alias pass='java -jar ~/passwordgrid.jar'
```

## Usage

```
# pass
```

### Example

The identifier pattern is of the form:

```
[<user>@]domain
```

However you can use any strings that represent a unique account.

For websites/services where you will only have one account (or the default account),
like facebook, linkedin, ...

```
# pass
> identifier: facebook
```

For a SSH server:

```
# pass
> identifier: user@myserver.com

```

If a website, like your bank, force you to change your password regularly, use
an incremental number as user part of the identifier:

```
# pass
> identifier: 1@bank
...


# pass
> identifier: 2@bank
...
```

# Documentation

## Passwords

In order to be accepted in most places, the password should have the following
characteristics:

- total length of 10+ characters
- a mix between lowercase, uppercase, number and [special char](https://www.owasp.org/index.php/Password_special_characters)

We propose to use a scheme like:

```
<8+ characters from the grids><postfix>
```

The postfix can be static or conditional. It can be used to add a lowercase or
an uppercase or a number if the PG haven't generated one, and also,
supply at least a special char.

A static postfix of 4 characters containing the full mix, like "Ye4!", can be a
good solution.

### Master password

You need the master password for each grids generation.

Choose it wisely, not too short and remember it.

If you input a wrong master password, the grids will just be different.

### Pattern

The pattern consists of picking a fixed amount of ordered chars in the grids.

By default, 4x3 grids of 64 characters are generated (ideally all the grids
should fit on the screen without needing to scroll).

We recommend a length of 8+ characters (~ 2800 billions possibilities) in order
to make brute force attempts difficult.

Once you have defined your pattern, you will reuse it all the time.

Someone observing you when you "extract" your password should not be able to
guess your pattern.

### Postfix

The postfix makes your password longer (and stronger as long an attacker does
not know it) and more compatible with specific password syntax exigences.

## Security

The grids are built with SHA-512 hashing:

```
SHA512(<master password>-<grid number>-<identifier>))
```

Each byte of the hash is then mapped to the corresponding alphabet character.

Why the system generates grids instead of directly giving a password ?

There are two advantages with grids:

- Even if your master password is compromised, an attacker will still have no
		access to your passwords without knowing your pattern.
- You can generate the grids in front of people and they will not be able to
		read your password.

### Limitations

![](http://imgs.xkcd.com/comics/security.png)

If an attacker gets one of your grid and its corresponding identifier, he could
have the ability to brute force the master password. Then the only protection
remaining will be your pattern. That's why, even the identifier input is hidden.

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

