% line comment

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
