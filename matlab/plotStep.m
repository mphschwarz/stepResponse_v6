%plots in- and output data and compares calculated step responses to data
function plotStep(t,x,y,t0,x0,tstart,tend,val,pmin,pmax,iter,di)

%plots processed input data and calculated step response
figure('Name','step response');
hold on;
plot(t(1:end-tstart+1),x(tstart:end));			% plots data vector
plot(t,y);						% plots caluculated data vector
legend('x(t) (input data)','y(t) (output data)');
xlabel('t [s]');
ylabel('signal');
hold off;

%plots raw input data, processed input data and step response
figure('Name','all data')
hold on;
plot(t0,x0);								%plots raw data vector
plot(t0(tstart:tstart+length(x(tstart:end))-1),x(tstart:end));		%plots parsed data vector
plot(t0(tstart:tstart+length(y(1:end-tstart))-1),y(1:end-tstart));	%plots calculated data vector
legend('x(t) (raw input)','x(t) (smoothed input)','y(t) (calculated output)');
xlabel('t [s]');
ylabel('signal');
hold off;

%plots calculated step response error for each number of poles
figure('Name','error/iter/offset')
subplot(1,3,1); title('Approximation error');
semilogy(linspace(pmin,pmax,length(val)),val);
xlabel('filter order');
ylabel('error sum (r^2)');

%plots number of iterations for each filter order
subplot(1,3,2); title('Iterations');
semilogy(linspace(pmin,pmax,pmax-pmin+1),iter(pmin:pmax));
xlabel('filter order');
ylabel('iterations');

% plots sample offset for each filter order
subplot(1,3,3); title('offset');
plot(linspace(pmin,pmax,pmax-pmin+1),di(pmin:pmax));
xlabel('filter order');
ylabel('sample offset');
end
