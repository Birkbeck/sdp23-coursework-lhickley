    mov EAX 6
    mov EBX 1
    mov ECX 1
f3: mul EBX EAX
    sub EAX ECX
    jnz EAX f3
    out EBX
    mov ESI 2
    mov EDX 90
    mov ESP 2
    mov EBP 3
f4: div EDX ESP
    div EDX EBP
    sub ESI ECX
    jnz ESI f4
    out EDX