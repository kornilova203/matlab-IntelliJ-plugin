% line comment

y = 1 ... comment
    -1

%{
block comment
%}

%{
block comment
  %{
    nested comment
  %}
%}

%{
%{ not start
not start %{
%}

%{
%} not end
not end %}
%}

myVar %{

42

%{
not closed block
