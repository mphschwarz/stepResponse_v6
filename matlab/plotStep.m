%plots in- and output data and compares calculated step responses to data
function plotStep(t,x,y,t0,x0,tstart,tend,val,pmin,pmax,iter)

%plots processed input data and calculated step response
figure('Name','step response');
plot(t(1:end-tstart+1),x(tstart:end));
hold on;
plot(t,y);
legend('x(t) (input data)','y(t) (output data)');
xlabel('time [t]');
ylabel('signal');
hold off;

%plots raw input data, processed input data and step response
figure('Name','all data')
hold on;
plot(t0,x0);	%plots raw data vector
plot(t0(tstart:tstart+length(x(tstart:end))-1),x(tstart:end));	%plots parsed data vector
plot(t0(tstart:tstart+length(y(1:end-tstart))-1),y(1:end-tstart));	%plots calculated data vector
legend('x(t) (raw input)','x(t) (smoothed input)','y(t) (calculated output)');
xlabel('time [t]');
ylabel('signal');
hold off;

%plots calculated step response error for each number of poles
figure('Name','error/iter')
subplot(1,2,1); title('Approximation error');
plot(linspace(pmin,pmax,length(val)),val);
xlabel('number of poles');
ylabel('sum of squared differences');

subplot(1,2,2); title('Iterations');
semilogy(linspace(pmin,pmax,pmax-pmin+1),iter(pmin:pmax));
xlabel('number of poles');
ylabel('iterations');
end
