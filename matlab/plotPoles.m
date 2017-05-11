%extracts and plots poles
%pmin, pmax: maximal and minimal number of poles
%c: coefficients from which to extract poles
%b: option for plotting a semicircle for comparison with butterworth filter
function plotPoles(c,pmin,pmax,b)
figure('Name','Poles');
hold on;
for n=pmin:pmax	% plots poles
	[re,im,k] = wqtoReIm(c(n,:),n);
	plot(re,im,'x');
end
if b == 1	% plots semicircle for comparison with butterworth filter
	plot(-cos(linspace(pi/2,-pi/2,200)),sin(linspace(pi/2,-pi/2,200)));
end
xlabel('Real');
ylabel('Imaginary');
hold off;
axis equal;
end
