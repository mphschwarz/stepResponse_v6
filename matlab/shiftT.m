function = shiftT(t,y,n,N)

[num,den] = genFraq(butterIniC(1,n,N),n); y = step(num,den,t);	% generates standard butterworth step resonpse


end
