clear vars; format shortG; close all;
load signale_2;

sf = 25;		%smoothing coefficient
zd = 0.004;		%noise amplitude for leading noise cut off
pmin = 1;		%minimum number of calculated poles
pmax = 10;		%maximum number of calculated poles
N = 10;			%maximum number of poles poles
tstart = 0;		%step time index (set to -1 for auto detect)
tend = 2000;		%trailing data cut off (set to -1 for auto detect)
yin = y11;		%sample data
tin = t11;		%sample time

%smoothes the input data and cuts off leading DC signals
[t,x,x0,tstart,tend] = parseData(yin,tin,zd,sf,tstart,tend);
%rescales time vector to match ripple base frequency
[t,T] = normT(x,t);
[n,c,y,val,iter,ef] = optStep(t,x,pmin,pmax,N);

plotStep(t,x,y(n,:),tin,yin,tstart,tend,val,pmin,pmax,iter);
plotPoles(c,n,n,1);

%finds optimal number of poles
%p: optimal number of poles
%c: pole values
%y: optimal step response
%val: error
function [p,c,y,val,iter,ef] = optStep(t,x,ns,ne,N)
options=optimset('TolX',1e-12,'MaxIter',5000,'MaxFunEvals',10000,'Display','final');

val = ones(N,1)*Inf;
y = ones(N,length(x));
iter = zeros(1,N);
ef = zeros(1,N)*2;

parfor r=ns:ne	%multithreaded for loop, calculates all orders in paralell
	[c(r,:),val(r),exitflag,output] = fminsearch(@(c) error(c,t,x,r,N),butterIniC(1,r,N),options);

	%collects data on fminsearch run time
	ef(r) = exitflag;	iter(r) = getfield(output, 'iterations')
	
	y(r,:) = stepResponse(c(r,:),t,r);
end
[~,p] = min(val);
end

%calculates step response from poles
%y: step response
%c: pole values
%n: number of poles
function y = stepResponse(c,t,n)
[num,den] = genFraq(c,n);
y = step(num,den,t);
end


%calculates step response error
function r = error(c,t,x,n,N)
%[di, ~] = shiftT(t,x,c,n,N);
%y = stepResponse(c,t(di:end),n);
%r = sum(y.^2-x(di:end).^2);
y = stepResponse(c,t,n);
r = sum((y - x).^2);
end