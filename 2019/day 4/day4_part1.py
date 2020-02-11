
def is_valid_password(password:int)->bool:
    prev = 0
    adjacent = False
    for c in str(password):
        if int(c) > prev:
            prev = int(c)
        elif int(c) == prev:
            prev = int(c)
            adjacent = True
        else:
            return False
    return True if adjacent else False


def number_of_passwords(input_data: range) -> int:
    npasswords = 0
    for value in input_data:
        if is_valid_password(value):
            npasswords+=1

    return npasswords


if __name__ == '__main__':
    input_data = range(134564, 585159)
    print(number_of_passwords(input_data))