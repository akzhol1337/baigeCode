//et editor = new CodeMirror.fromTextArea();
let language = null;
let editor = null;
window.onload = () => {
    const[input, output] = document.querySelectorAll(".solutionbar");
    let theme = document.getElementById('theme');
    editor = CodeMirror.fromTextArea(input, {
        mode: "text/x-java",
        lineNumbers: true,
        extraKeys: {"Ctrl-E": "autocomplete"},
        autoCloseBrackets: true,
        lineWrapping: true,
        theme: "abbott"
    });


    editor.on('Tab', function(editor, event){
        CodeMirror.commands.autocomplete(editor)
    });
    editor.getDoc().setValue(java_code)
    

    
    // myCodeMirror.on("keyup", function (cm, event) {
    //     if (!cm.state.completionActive && /*Enables keyboard navigation in autocomplete list*/
    //         event.keyCode != 13) {        /*Enter - do not open autocomplete list just after item has been selected in it*/ 
    //         CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
    //     }
    // });
}

function change_theme(){
    let theme = document.getElementById('theme');

    if(theme.value == 0){
        editor.setOption("theme", "abbott")
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

class Rextester
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