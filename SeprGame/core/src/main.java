import combat.items.Weapon;
import combat.items.WeaponBank;

public class main {
    public static void main(String[] args){
        System.out.println("TESTING SCRATCHPAD");
        Weapon myWeapon = WeaponBank.values()[0].getWeapon();
        Weapon weapon2 = WeaponBank.values()[0].getWeapon();
        System.out.println(myWeapon.cost);
        System.out.println(weapon2.cost);
        System.out.println("");
        myWeapon.cost = 3;
        System.out.println(myWeapon.cost);
        System.out.println(weapon2.cost);

    }
}
