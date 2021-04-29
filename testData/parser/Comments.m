% line comment

# line comment

y = 1 ... comment
    -1
y = [1, ...c
     2]

%{
block comment
%}

#{
block comment
#}

%{
block comment
  %{
    nested comment
  %}
%}

#{
block comment
  #{
    nested comment
  #}
#}

%{
%{ not start
not start %{
%}

%{
%} not end
not end %}
%}

#{
#{ not start
not start #{
#}

#{
#} not end
not end #}
#}

myVar %{

42

%{
not closed block
