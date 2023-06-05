// for switching like, dislike buttons
const svg1 = document.querySelector('.svg-1');
const svg2 = document.querySelector('.svg-2');

svg1.addEventListener('click', () => {
  svg1.style.display = 'none';
  svg2.style.display = 'block';
});

svg2.addEventListener('click', () => {
  svg2.style.display = 'none';
  svg1.style.display = 'block';
});


const svg3 = document.querySelector('.svg-3');
const svg4 = document.querySelector('.svg-4');

svg3.addEventListener('click', () => {
    svg3.style.display = 'none';
    svg4.style.display = 'block';
});

svg4.addEventListener('click', () => {
  svg4.style.display = 'none';
  svg3.style.display = 'block';
});





//et editor = new CodeMirror.fromTextArea();
let language = null;
let editor = null;
window.onload = function(){
  window.alert('123');
  const[input, output] = document.querySelectorAll(".solutionbar");
  let theme = document.getElementById('theme');
  editor = CodeMirror.fromTextArea(input, {
      mode: "text/x-java",
      lineNumbers: true,
      extraKeys: {"Tab": "autocomplete"},
      autoCloseBrackets: true,
      lineWrapping: true,
      theme: "default",
  });


  editor.on('Tab', function(editor, event){
      CodeMirror.commands.autocomplete(editor)
  });
  editor.getDoc().setValue(java_code)
}

function change_theme(){
    let theme = document.getElementById('theme');

    if(theme.value == 0){
        editor.setOption("theme", "default")
    }else if(theme.value == 1){
        editor.setOption("theme", "dracula")
    }else if(theme.value == 2){
        editor.setOption("theme", "material")
    }
}

function change_language(){
    let lang = document.getElementById('lang');
    let samplecode = document.getElementById('samplecode');

    


    if(lang.value == 0){
        editor.setOption("mode", "text/x-java")
        editor.getDoc().setValue(java_code)
        //samplecode.innerHTML = java_code;
        //console.log($('sample-code').val());
        //console.log(samplecode.innerHTML)
        //editor.getDoc().setValue
        
    }else if(lang.value == 1){
        editor.setOption("mode", "text/x-python")
        editor.getDoc().setValue(python_code)
        //document.getElementById('source-code').innerHTML = python_code;
    }else if(lang.value == 2){
        editor.setOption("mode", "text/x-c++src")
        editor.getDoc().setValue(cpp_code)
        //document.getElementById('source-code').innerHTML = cpp_code;
    }
}

java_code = 
`import java.util.*;
import java.lang.*;

class Solution
{  
    public static void main(String args[])
    {
        System.out.println("Hello, World!");
    }
}`;

cpp_code = 
`#include <iostream>

using namespace std;
int main()
{
    cout << "Hello, world!";
}`;

python_code = `print("Hello world")`;