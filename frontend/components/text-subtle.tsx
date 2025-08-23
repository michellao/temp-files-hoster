import { Text, type TextProps } from "@radix-ui/themes";

const TextSubtle = (props: TextProps) => (
  <Text as="p" size="2" color="gray" {...props} />
);

export default TextSubtle;
