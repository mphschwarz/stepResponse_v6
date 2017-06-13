
clc,clear all,close all hidden
load signale_2.mat;
load signale_1.mat;
load signale_3.mat;

B=[	3.1557944503918287E29];
A = [	1.0,	146526.81328418074,	3.045792885840797E10,	2.262488603567027E15,	2.3084692051810222E20,	6.069385770071659E24,	3.993021704721282E29];

t12s=t12(1:2250);
y12s=y12(1:2250)-2;


[step_ist]=step(B,A,t12);
plot(t12,step_ist);
roots(A)
figure(2)
plot(t12s,y12s,t12,step_ist),grid on

