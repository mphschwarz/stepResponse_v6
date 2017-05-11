clear vars; format shortG; close all;
load signale_1;
load signale_2;
load signale_3;

sf = 25;		%smoothing coefficient
zd = 0.004;		%noise amplitude for leading noise cut off
pmin = 1;		%minimum number of calculated poles
pmax = 10;		%maximum number of calculated poles
N = 10;			%maximum number of poles poles
tstart = 1;		%step time index (set to -1 for auto detect)
tend = 1000;		%trailing data cut off (set to -1 for auto detect)
yin = y15;		%sample data
tin = t15;		%sample time

global di;
di = ones(N,1);


tic	% starts run time measurement
%smoothes the input data and cuts off leading DC signals
[t,x,x0,tstart,tend] = parseData(yin,tin,zd,sf,tstart,tend);
[t,T] = normT(x,t);

[n,c,y,val,iter,ef] = optStep(t,x,pmin,pmax,N);

plotStep(t,x,y(n,:),tin,yin,di(n),tend,val,pmin,pmax,iter,di);
plotPoles(c,n,n,1);

toc	% displays run time

%finds optimal number of poles
%p: optimal number of poles
%c: pole values
%y: optimal step response
%val: error
function [p,c,y,val,iter,ef] = optStep(t,x,ns,ne,N)
options=optimset('TolX',1e-12,'MaxIter',5000,'MaxFunEvals',10000,'Display','final');
global di;		% 
val = ones(N,1)*Inf;	% error for each filter order
y = ones(N,length(x));	% calculated step response for each filter order
iter = zeros(1,N);	% number of iterations of fminsearch
ef = zeros(1,N)*2;	% exit flag for fminsearch
d = ones(N,1);		% sample shift

parfor r=ns:ne	%multithreaded for loop, calculates all orders in paralell
	[c(r,:),val(r),exitflag,output] = fminsearch(@(c) error(c,t,x,r,N),butterIniC(1,r,N),options);
	
	[td,~,~] = shiftT(t,x,c(r,:),r,N);
	if td < 0	% catches negtive shift values
		d(r) = 1;	% sets shft value to 1 if otherwise negative
	else
		d(r) = td;	% sets shift value
	end
	
	%collects data on fminsearch run time
	ef(r) = exitflag;	iter(r) = getfield(output, 'iterations');
	
	y(r,:) = stepResponse(c(r,:),t,r);
end
[~,p] = min(val);	% finds best filter order
di = d;
end

%calculates step response from poles
%y: step response
%c: pole values
%n: number of poles
function y = stepResponse(c,t,n)
[num,den] = genFraq(c,n);	% genera
y = step(num,den,t);
end


%calculates step response error 
function r = error(c,t,x,n,N)
global di
[d, ~, ~] = shiftT(t,x,c,n,N);	% calculates sample shift based on butterworth step response
%di(n) = d;
if d < 0	% prevents negative sample shifts
	d = 1;
end
y = stepResponse(c,t(1:end-d+1),n);
%[d,n, length(t), length(x), length(y)]
%ly = length(y(1:end-d));
%lx = length(x(d:end));
r = sum((y(1:end)-x(d:end)).^2);
%{
y = stepResponse(c,t,n);
r = sum((y - x).^2);
%}
end
