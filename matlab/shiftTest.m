close all;
clear all;
load signale_1;
load signale_2;
load signale_3;

sf = 25;		%smoothing coefficient
zd = 0.004;		%noise amplitude for leading noise cut off
pmin = 1;		%minimum number of calculated poles
pmax = 10;		%maximum number of calculated poles
N = 10;			%maximum number of poles poles
tstart = 0;		%step time index (set to -1 for auto detect)
tend = 2000;		%trailing data cut off (set to -1 for auto detect)
yin = y11;		%sample data
tin = t11;		%sample time

n = 5;		% filter order
ds = 200;	% number of zeros inserted before
S = 2500;	% number of total samples
[num,den] = genFraq(butterIniC(1,n,10),n);	% generates polynom of butterworth filter

tx = linspace(0,20,S);
x = step(num,den,tx(1:S-ds));
x = [zeros(1,ds),x'];
[tx,T] = normT(x,tx);

[di,~,~] = shiftT(tx,x,1,n,10)

ty = linspace(0,20,S);
y = step(num,den,ty(1:S-di));
y = [zeros(1,di),y'];
[ty,T] = normT(y,ty);


tz = linspace(0,20,length(y11));
[~,z,~,~,~] = parseData(y11,tz,0.4,25,1,length(y11));
[tz,T] = normT(z,tz);

[dz,~,~] = shiftT(tz,z,1,n,10)


figure(1)
hold on;
plot(x)
plot(y)
plot(z);
hold off;

figure(2)
hold on;
plot(tx,x);
plot(ty,y);
plot(tz,z);
hold off;

figure(3)
hold on;
plot(tz(1:end-dz),z(1+dz:end));
plot(tx(1:end-ds),x(1+ds:end));
hold off;




%{
%smoothes the input data and cuts off leading DC signals
%[t,x,x0,tstart,tend] = parseData(yin,tin,zd,sf,tstart,tend);
%plot(t,x);
[num,den] = genFraq(butterIniC(1,5,10),5);
x = step(num,den,linspace(0,20,1001));
x = [zeros(1,200),x'];
t = linspace(0,20,length(x));
[t, T] = normT(x,t);


n = 5;
c = butterIniC(1,n,N);
[num,den] = genFraq(c,n);
xb = step(num,den,t(200:end));
%plot(t,x);

[di,dt,tb] = shiftT(t,x,c,n,N);
%plot(tb,y);
di
plot(t(1:-di+end),x(1+di:end));
[tb,T] = normT(xb,tb);
plot(tb(200:end),xb);

hold off;
%}
